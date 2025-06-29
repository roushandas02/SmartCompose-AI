import React, { useState } from "react";
import axios from "axios";

function Register () {

    const [register, setRegister] = useState({
        "userName": "",
        "password": ""
    });

    const handleChange = (e) => {
      setRegister({
        ...register,
        [e.target.name]: e.target.value
      });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(register);

        try {
            const response = await axios.post('http://localhost:8080/public/signup', register);
            console.log(response.data);
            alert("User added successfully");
       } catch (error) {
            console.log(error);
       }
    };
    const redirectToLogin = () => {
        window.location.href = "/login";
    };
    
    return (
       <div className="container ">
          <div class="card ">
            <div class="card-header">
              Sign Up to SmartCompose AI
            </div>
            <div class="card-body">
              <form className="container my-auto" onSubmit={handleSubmit}>
                <h1 className="text-center">Register</h1>

                {/* <label>Name:</label>
                <input type="text" name="name" placeholder="Enter your name" value={register.name} onChange={handleChange} /> */}
                <div class="mb-3 m-auto w-50">
                  <label for="exampleFormControlInput1" class="form-label">Username</label>
                  <input type="text" name="userName" class="form-control" id="exampleFormControlInput1" placeholder="Enter your username" value={register.userName} onChange={handleChange}/>
                </div>
                

                {/* <label>Email:</label>
                <input type="email" name="email" placeholder="Enter your email" value={register.email} onChange={handleChange} /> */}
                {/* <div class="mb-3 m-auto w-50">
                  <label for="exampleFormControlInput1" class="form-label">Email</label>
                  <input type="email" class="form-control" id="exampleFormControlInput1" name="email" placeholder="Enter your email" value={register.email} onChange={handleChange}/>
                </div> */}
                

                {/* <label>Password:</label>
                <input type="password" name="password" placeholder="Enter your password" value={register.password} onChange={handleChange} /> */}
                <div class="mb-3 m-auto w-50">
                  <label for="exampleFormControlInput1" class="form-label">Password</label>
                  <input type="password" class="form-control" id="exampleFormControlInput1" name="password" placeholder="Enter your password" value={register.password} onChange={handleChange}/>
                </div>
                
                <div class="mb-3 m-auto w-50">
                  <button className="btn btn-success " type="submit">Register</button>
                </div>
                
              </form>
            </div>
            <div class="card-footer text-body-secondary">
                  <p>Already have an account? <a className="btn btn-primary" onClick={redirectToLogin}>Login</a></p>
            </div>
          </div>
          
       </div>
    );
}

export default Register;