import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import AuthenticationService from "../service/AuthenticationService";

const input = [
    "w-96",
    "px-3",
    "py-0.5",
    "mt-1",
    "mb-4",
    "rounded-lg",
    "bg-search"
].join(' ');

function Login() {
    let navigate = useNavigate();
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const [message, setMessage] = useState();
    const handleSubmit = async (e) => {
        e.preventDefault();

        AuthenticationService.login(email, password).then(
            () => {
                navigate('/');
            },
            (error) => {
                setMessage("Invalid email or password");
            }
        );
    };

    return (
        <>
            <div className={[
                "bg-page",
                "flex",
                "flex-auto",
                "justify-center",
                "content-center",
                "items-center",
                "h-screen"
            ].join(' ')}>
                <div className={"flex flex-col items-center content-center p-10 rounded-xl border-solid border-2 border-menu shadow-xl"}>

                    <img src="/image/logo.png" alt="Tunemate"/>

                    <form onSubmit={handleSubmit} className={"mt-3 mx-5 text-white text-lg font-light"}>

                        <h1>Email</h1>
                        <input type={"text"} placeholder={"Email address"} className={input} value={email}
                               onChange={e => setEmail(e.target.value)}/>

                        <h1>Password</h1>
                        <input type={"password"} placeholder={"Password"} className={input} value={password}
                               onChange={e => setPassword(e.target.value)}/>

                        <div>{message}</div>

                        <div className={"my-2"}>
                            <input type={"submit"} value={"Sign in"}
                                   className={["w-24", "font-medium", "mr-3", "py-0.5", "rounded-lg", "bg-blue-600", "hover:bg-blue-500"].join(' ')}/>
                        </div>

                        <div className={"flex justify-center"}>
                            <h1>Don't have an account? <a href={"/register"} className={"font-medium"}>Register</a></h1>
                        </div>

                    </form>

                </div>

            </div>
        </>
    );
}

export default Login;