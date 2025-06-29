document.addEventListener('DOMContentLoaded', () => {
  const emailContentInput = document.getElementById('emailContent');
  const toneSelect = document.getElementById('tone');
  const customPromptInput = document.getElementById('customPrompt');
  const generateBtn = document.getElementById('generateBtn');
  const loadingIndicator = document.getElementById('loading');
  const errorDiv = document.getElementById('error');
  const replySection = document.getElementById('replySection');
  const generatedReplyTextarea = document.getElementById('generatedReply');
  const copyBtn = document.getElementById('copyBtn');

  generateBtn.addEventListener('click', async () => {
    

    const emailContent = emailContentInput.value.trim() ;
    const tone = toneSelect.value;
    const customPrompt = customPromptInput.value.trim();

    if (!emailContent) {
      errorDiv.textContent = 'Please enter the email content.';
      return;
    }

    // Reset UI
    errorDiv.textContent = '';
    replySection.style.display = 'none';
    loadingIndicator.style.display = 'block';
    generateBtn.disabled = true;

    try {
      const response = await fetch('http://localhost:8080/api/email/generate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          emailContent,
          tone,
          customPrompt
        })
      });

      if (!response.ok) {
        throw new Error('API Request failed');
      }

      const text = await response.text();
      generatedReplyTextarea.value = text;
      replySection.style.display = 'block';
    } catch (error) {
      console.error(error);
      errorDiv.textContent = 'Failed to generate email reply. Please try again.';
    } finally {
      loadingIndicator.style.display = 'none';
      generateBtn.disabled = false;
    }
  });

  copyBtn.addEventListener('click', () => {
    generatedReplyTextarea.select();
    document.execCommand('copy');
  });
});
