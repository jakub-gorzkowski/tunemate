import { Icon } from '@iconify/react';
import React from "react";
import axios from "axios";

const icons = [
    "w-7",
    "h-7",
    "mr-2",
    "text-menu"
].join(' ');

function PlaylistHeader({ playlist }) {
    const handleAddToFavourites = () => {
        const user = JSON.parse(localStorage.getItem('user'));
        const token = user && user.jwtToken;
        const spotifyId = playlist.spotifyId;

        axios.patch(`http://localhost:8080/api/user/add-favourite-playlist/${spotifyId}`, {}, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(response => {
                console.log(response);
            })
            .catch(error => {
                console.error(error);
            });
    };

    return (
        <>
            <div className={"w-full flex flex-col items-center mt-36"}>
                <div className={[
                    "w-content",
                    "flex",
                    "items-end"
                ].join(' ')}>
                    <div className={[
                        "w-40",
                        "h-40",
                        "bg-cover-placeholder",
                        "rounded-lg"
                    ].join(' ')} style={{
                        backgroundImage: `url(${playlist.photoUrl})`,
                        backgroundSize: 'cover',
                        backgroundPosition: 'center'
                    }}></div>
                    <div className={"w-64 ml-4 text-white my-3"}>
                        <h1 className={"text-4xl font-bold my-2"}>{playlist.title}</h1>
                        <div className={"flex my-1"}>
                            <a href={`https://open.spotify.com/playlist/${playlist.spotifyId}`}><Icon
                                icon={"mdi:spotify"}
                                className={icons + " hover:text-green-500"}/></a>
                            <button onClick={handleAddToFavourites}
                                    className=""><Icon
                                icon={"mdi:heart"}
                                className={icons + " hover:text-red-500"}/>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default PlaylistHeader;