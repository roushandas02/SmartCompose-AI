console.log("Email Writer Extension - Content Script Loaded");

//to create our button to be injected in toolbar of gmail
function createAIButton() {
   const button = document.createElement('div');
   //"send email" button classes of gmail(got from inspect)----giving same classname to our button
   button.className = 'T-I J-J5-Ji aoO v7 T-I-atl L3';
   button.style.marginRight = '8px';
   button.innerHTML = 'AI Reply';
   button.setAttribute('role','button');
   button.setAttribute('data-tooltip','Generate AI Reply');
   return button;
}

//get the email content to which we want to send reply
function getEmailContent() {
    //classess of mail (subject, body, quoted text and all)--got from inspect----giving this as a prompt for gemini api
    const selectors = [
        '.h7',
        '.a3s.aiL',
        '.gmail_quote',
        '[role="presentation"]'
    ];
    for (const selector of selectors) {
        const content = document.querySelector(selector);
        if (content) {
            return content.innerText.trim();
        }
        return '';
    }
}

//to find the gmail toolbaar if present or not - where the button will be injected
function findComposeToolbar() {
    //compose window/dialog box classes from inspect in gmail
    const selectors = [
        '.btC',
        '.aDh',
        '[role="toolbar"]',
        '.gU.Up'
    ];
    for (const selector of selectors) {
        const toolbar = document.querySelector(selector);
        if (toolbar) {
            return toolbar;
        }
        return null;
    }
}


function injectButton() {
    //we are naming our button this, so if by chance its already present, remove that
    const existingButton = document.querySelector('.ai-reply-button');
    if (existingButton) existingButton.remove();

    //finding gmail toolbar where button will be injected
    const toolbar = findComposeToolbar();
    if (!toolbar) {
        console.log("Toolbar not found");
        return;
    }

    console.log("Toolbar found, creating AI button");
    const button = createAIButton();
    button.classList.add('ai-reply-button');

    //on button click, fetch generated reply by gemini api from springboot backend endpoint
    button.addEventListener('click', async () => {
        try {
            button.innerHTML = 'Generating...';
            button.disabled = true;

            //getting content of the email from gmail interface to which we want to send reply
            const emailContent = getEmailContent();
            


            //fetch api call to springboot backend
            const response = await fetch('http://localhost:8080/api/email/generate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    emailContent: emailContent,
                    tone: "professional"
                })
            });

            if (!response.ok) {
                throw new Error('API Request Failed');
            }

            const generatedReply = await response.text();
            //classes of gmail compose mail textbox --- to insert the generated reply
            const composeBox = document.querySelector('[role="textbox"][g_editable="true"]');

            if (composeBox) {
                composeBox.focus();
                //gmail internal command to insert the generated reply to gmail compose textbox
                document.execCommand('insertText', false, generatedReply);
            } else {
                console.error('Compose box was not found');
            }
        } catch (error) {
            console.error(error);
            alert('Failed to generate reply');
        } finally {
            button.innerHTML = 'AI Reply';
            button.disabled =  false;
        }
    });

    //insert our button before first child of toolbar 
    toolbar.insertBefore(button, toolbar.firstChild);
}




// ------------------------------------------------------------------------------------------------------------------------------------------------------





//like browser api who watches all the changes in the DOM
const observer = new MutationObserver((mutations) => {
    for(const mutation of mutations) {
        //All the changes in the nodes of browser added to this array (for ex - dialog box open, reply box open, compose box open, etc)
        const addedNodes = Array.from(mutation.addedNodes);
        const hasComposeElements = addedNodes.some(node =>
            node.nodeType === Node.ELEMENT_NODE && 
            //name of class of the element in the browser
            (node.matches('.aDh, .btC, [role="dialog"]') || node.querySelector('.aDh, .btC, [role="dialog"]'))
        );

        //if compose window in gmail detected
        if (hasComposeElements) {
            console.log("Compose Window Detected");
            setTimeout(injectButton, 500);
        }
    }
});

//start observing
observer.observe(document.body, {
    childList: true,
    subtree: true
});