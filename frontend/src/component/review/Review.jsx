import React from "react";
import { Link } from "react-router-dom";
import Votes from "./Votes";
import PropTypes from "prop-types";

function Review({ review }) {
    return (
        <div className={"px-4 py-3 my-6 w-review border-b border-menu flex flex-col items-center hover:bg-page-hover"}>
            <div className={"w-full"}>
                <div className={"flex flex-auto items-center"}>
                    <div className={"flex flex-col mb-2"}>
                        <h1 className={"text-white text-xl mb-2"}>
                            <Link to={`/user/${review.userDto.id}`} className="text-thin text-menu hover:underline">
                                {review.userDto.username}
                            </Link>
                        </h1>
                    </div>
                    <div className={"w-full"}/>
                    <Votes/>
                </div>
                <h2 className={"font-thin text-menu"}>{review.content}</h2>
            </div>
        </div>
    );
}

Review.propTypes = {
    review: PropTypes.object.isRequired
};

export default Review;
