import React, { useState } from 'react';
import axios from 'axios';

const input = [
    "w-96",
    "px-3",
    "py-0.5",
    "mt-1",
    "mb-4",
    "rounded-lg",
    "bg-search"
].join(' ');

function AccountDetails() {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [spotifyId, setSpotifyId] = useState('');

    const user = JSON.parse(localStorage.getItem('user'));
    const token = user && user.jwtToken;

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (password !== confirmPassword) {
            alert("Passwords do not match");
            return;
        }

        const payload = {
            username,
            email,
            password,
            spotifyId
        };

        // Remove empty fields from payload
        Object.keys(payload).forEach(key => {
            if (!payload[key]) {
                delete payload[key];
            }
        });

        try {
            const response = await axios.patch('http://localhost:8080/api/user/update', payload, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.status === 200) {
                alert('Account updated successfully');
            } else {
                alert('Failed to update account');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('An error occurred');
        }
    };

    return (
        <>
            <div>
                <h1 className={[
                    "text-title",
                    "text-xl",
                    "font-medium",
                    "mx-5",
                    "my-2",
                ].join(' ')}>Account details</h1>
                <div className={[
                    "flex",
                    "flex-col",
                    "w-content"
                ].join(' ')}>
                    <form onSubmit={handleSubmit} className={"mt-3 mx-5 text-white text-lg font-light"}>
                        <h1>Username</h1>
                        <input
                            type={"text"}
                            placeholder={"Username"}
                            className={input}
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                        <h1>Email</h1>
                        <input
                            type={"text"}
                            placeholder={"Email address"}
                            className={input}
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                        <h1>Password</h1>
                        <input
                            type={"password"}
                            placeholder={"Password"}
                            className={input}
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                        <h1>Confirm password</h1>
                        <input
                            type={"password"}
                            placeholder={"Confirm password"}
                            className={input}
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                        />
                        <h1>Spotify</h1>
                        <input
                            type={"text"}
                            placeholder={"Enter address"}
                            className={input}
                            value={spotifyId}
                            onChange={(e) => setSpotifyId(e.target.value)}
                        />
                        <div className={"my-2"}>
                            <input
                                type={"submit"}
                                value={"Update"}
                                className={["w-24", "font-medium", "mr-3", "py-0.5", "rounded-lg", "bg-blue-600", "hover:bg-blue-500"].join(' ')}
                            />
                            <input
                                type={"reset"}
                                value={"Cancel"}
                                className={["w-24", "font-thin", "mr-3", "py-0.5", "rounded-lg", "bg-page-hover", "hover:bg-gray-400"].join(' ')}
                                onClick={() => {
                                    setUsername('');
                                    setEmail('');
                                    setPassword('');
                                    setConfirmPassword('');
                                    setSpotifyId('');
                                }}
                            />
                        </div>
                    </form>
                </div>
            </div>
        </>
    );
}

export default AccountDetails;
