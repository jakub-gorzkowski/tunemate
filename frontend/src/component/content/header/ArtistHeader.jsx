import React from "react";
import { Icon } from '@iconify/react';
import PropTypes from "prop-types";
import axios from "axios";

const icons = [
    "w-7",
    "h-7",
    "mr-2",
    "text-menu"
].join(' ');

function formatNumber(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
}

function ArtistHeader({ artist }) {
    if (!artist) {
        return <div>Loading...</div>;
    }

    const handleFollowArtist = () => {
        const user = JSON.parse(localStorage.getItem('user'));
        const token = user && user.jwtToken;
        const spotifyId = artist.spotifyId;

        axios.patch(`http://localhost:8080/api/user/follow/${spotifyId}`, {}, {
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
            <div className={"w-full flex flex-col items-center mt-44"}>
                <div className={[
                    "w-content",
                    "flex",
                    "items-center"
                ].join(' ')}>
                    <div className={[
                        "w-40",
                        "h-40",
                        "bg-cover",
                        "rounded-full"
                    ].join(' ')} style={{ backgroundImage: `url(${artist.photoUrl})` }}></div>
                    <div className={"w-64 ml-4 text-white flex flex-col my-1"}>
                        <h1 className={"text-4xl font-bold"}>{artist.name}</h1>
                        <h2>{formatNumber(artist.followerCount)} Followers</h2>
                        <div className="flex mt-2">
                            <a href={`https://open.spotify.com/artist/${artist.spotifyId}`}><Icon icon={"mdi:spotify"} className={icons + " hover:text-green-500"}/></a>
                            <button onClick={handleFollowArtist}
                                    className="mx-1 px-2 rounded bg-blue-500 hover:bg-blue-600 text-white">Follow
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

ArtistHeader.propTypes = {
    artist: PropTypes.object.isRequired
};

export default ArtistHeader;
