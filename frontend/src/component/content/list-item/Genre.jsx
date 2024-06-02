import React from "react";
import PropTypes from "prop-types";
import axios from "axios";
import {Icon} from "@iconify/react";

const icons = [
    "w-7",
    "h-7",
    "mr-2",
    "text-menu"
].join(' ');

function Genre(props) {

    const handleAddGenre = () => {
        const user = JSON.parse(localStorage.getItem('user'));
        const token = user && user.jwtToken;
        const genreName = props.data.genre;

        axios.patch(`http://localhost:8080/api/user/add-genre/${genreName}`, {}, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .catch(error => {
                console.error(error);
            });
    };

    const handleRemoveGenre = () => {
        const user = JSON.parse(localStorage.getItem('user'));
        const token = user && user.jwtToken;
        const genreName = props.data.genre;

        axios.delete(`http://localhost:8080/api/user/remove-genre/${genreName}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .catch(error => {
                console.error(error);
            });
    };

    return (
        <>
            <div className={[
                "flex",
                "flex-col",
                "items-center",
                "m-1",
                "p-4",
                "rounded-lg",
                "bg-genre",
                "hover:bg-page-hover"
            ].join(' ')}>
                <div className={[
                    "w-40",
                    "flex"
                ].join(' ')}>
                    <p className={[
                        "text-menu",
                        "font-light"
                    ].join(' ')}>{props.data.genre}</p>
                    <div className={"w-40"}/>
                    <div className="flex mt-2">
                        <button onClick={handleAddGenre}
                                className=""><Icon
                            icon={"mdi:plus"}
                            className={icons + " hover:text-green-500"}/></button>
                        <button onClick={handleRemoveGenre}
                                className=""><Icon
                            icon={"mdi:minus"}
                            className={icons + " hover:text-red-500"}/></button>
                    </div>
                </div>
            </div>
        </>
    );
}

Genre.propTypes = {
    data: PropTypes.object.isRequired
};

export default Genre;
