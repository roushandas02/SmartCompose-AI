import React, { useState } from "react";
import axios from "axios";

function Login() {
    
    const [password, setPasswordValue] = useState("");
    const [userId, setUserIdValue] = useState("");

    const setPassword = (e) => {
        setPasswordValue(e.target.value);
    };

    const setUserId = (e) => {
        setUserIdValue(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault(); // Prevent default form submission

        console.log("this is our data " + userId + "   " + password);

        const data = {
            "username": userId,
            "password": password
        };

        try {
            const response = await axios.post("http://localhost:8080/signin", data);
            console.log("this is the response " + response.data);

            if (!response.data) {
                alert("Invalid User Id or Password");
            } else {
                alert("Login Successful");
                window.location.href = "/";
            }

        } catch (error) {
            console.error(error);
        }
    };

    const redirectToRegister = () => {
        window.location.href = "/register";
    };

    return (
        <div className="container ">
          <div class="card ">
            <div class="card-header">
              Log In to SmartCompose AI
            </div>
            <div class="card-body">
              <form className="container my-auto" onSubmit={handleSubmit}>
                <h1 className="text-center">Log In</h1>
                

                {/* <label>Email:</label>
                <input type="email" name="email" placeholder="Enter your email" value={register.email} onChange={handleChange} /> */}
                <div class="mb-3 m-auto w-50">
                  <label for="exampleFormControlInput1" class="form-label">Username</label>
                  <input type="text" class="form-control" id="exampleFormControlInput1" name="username" placeholder="Enter your username" value={userId} onChange={setUserId}/>
                </div>
                

                {/* <label>Password:</label>
                <input type="password" name="password" placeholder="Enter your password" value={register.password} onChange={handleChange} /> */}
                <div class="mb-3 m-auto w-50">
                  <label for="exampleFormControlInput1" class="form-label">Password</label>
                  <input type="password" class="form-control" id="exampleFormControlInput1" name="password" placeholder="Enter your password" value={password} onChange={setPassword}/>
                </div>
                
                <div class="mb-3 m-auto w-50">
                  <button className="btn btn-success " type="submit">Login</button>
                </div>
              </form>
            </div>
            <div class="card-footer text-body-secondary">
                <p>Don't have an account? <a className="btn btn-primary" onClick={redirectToRegister}>Signup</a></p>
            </div>
          </div>
          
       </div>
        
    );
}

export default Login;
