import React from "react";
import PropTypes from "prop-types";
import axios from "axios";

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
                                className="rounded bg-green-500 hover:bg-green-600 text-white font-bold px-2 h-8 w-8">+
                        </button>
                        <button onClick={handleRemoveGenre}
                                className="rounded bg-red-500 hover:bg-red-600 text-white font-bold px-2 h-8 w-8 ml-1">-
                        </button>
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
