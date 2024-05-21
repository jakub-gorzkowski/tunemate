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

function Register() {
    let navigate = useNavigate();
    const [email, setEmail] = useState();
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();
    const [passwordConfirmation, setPasswordConfirmation] = useState();
    const [message, setMessage] = useState();

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (password !== passwordConfirmation) {
            setMessage(["Passwords aren't the same"]);
            return;
        }

        try {
            const response = await AuthenticationService.register(username, email, password);

            switch (response.status) {
                case 200:
                    setMessage("You have created an account");
                    navigate("/login");
                    break;
                default:
                    setMessage(typeof response.data === 'object' ? response.data.error : response.data);
            }

        } catch (error) {
            setMessage(error.message);
        }
    }

    return (
        <>
            <div className={[
                "bg-page",
                "w-screen",
                "flex",
                "flex-auto",
                "justify-center",
                "items-center",
                "h-screen"
            ].join(' ')}>
                <div className={"flex flex-col items-center content-center p-10 rounded-xl border-solid border-2 border-menu shadow-xl"}>

                    <img src="/image/logo.png" alt="Tunemate"/>

                    <form method={"post"} onSubmit={handleSubmit} className={"mt-3 mx-5 text-white text-lg font-light"}>

                        <h1>Email</h1>
                        <input type={"text"} value={email} placeholder={"Email address"} className={input} onChange={e => setEmail(e.target.value)}/>

                        <h1>Username</h1>
                        <input type={"text"} value={username} placeholder={"Username"} className={input} onChange={e => setUsername(e.target.value)}/>

                        <h1>Password</h1>
                        <input type={"password"} value={password} placeholder={"Password"} className={input} onChange={e => setPassword(e.target.value)}/>

                        <h1>Confirm password</h1>
                        <input type={"password"} value={passwordConfirmation} placeholder={"Confirm password"} className={input} onChange={e => setPasswordConfirmation(e.target.value)}/>

                        <div>{message}</div>

                        <div className={"my-2"}>
                            <input type={"submit"} value={"Sign up"}
                                   className={["w-24", "font-medium", "mr-3", "py-0.5", "rounded-lg", "bg-blue-600", "hover:bg-blue-500"].join(' ')}/>
                        </div>

                        <div className={"flex justify-center"}>
                            <h1>Already have an account? <a href={"/login"} className={"font-medium"}>Login</a></h1>
                        </div>

                    </form>

                </div>

            </div>
        </>
    );
}

export default Register;