import React, { useState } from "react";
import axios from "axios";

function Review({ release }) {
    const [comment, setComment] = useState("");

    const handleInputChange = (event) => {
        setComment(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        const user = JSON.parse(localStorage.getItem('user'));
        const token = user && user.jwtToken;

        axios.post(`http://localhost:8080/api/releases/add-review/${release.spotifyId}`,
            {
                content: comment
            },
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
            .then(response => {
                console.log(response.data);
                setComment("");
                window.location.reload();
            })
            .catch(error => {
                console.error(error);
            });
    };

    return (
        <>
            <div className={"mt-10"}>
                <h1 className={[
                    "text-title",
                    "text-xl",
                    "font-medium",
                    "mx-4",
                    "my-2"
                ].join(' ')}>Share your thoughts</h1>
                <div className={"p-4 flex flex-col"}>
                    <form onSubmit={handleSubmit}>
                        <textarea
                            placeholder={"Enter your comment here"}
                            value={comment}
                            onChange={handleInputChange}
                            className={"bg-genre w-review mt-4 p-4 h-40 min-h-24 max-h-56 rounded-xl"}
                        />
                        <div className={"flex flex-auto justify-end mt-4"}>
                            <input
                                type={"submit"}
                                value={"Post comment"}
                                className={["w-32", "font-medium", "mr-3", "py-0.5", "rounded-lg", "bg-blue-600", "hover:bg-blue-500"].join(' ')}
                            />
                            <input
                                type={"reset"}
                                value={"Cancel"}
                                className={["w-20", "font-thin", "py-0.5", "rounded-lg", "bg-page-hover", "hover:bg-gray-400"].join(' ')}
                                onClick={() => setComment("")}
                            />
                        </div>
                    </form>
                </div>
            </div>
        </>
    );
}

export default Review;
