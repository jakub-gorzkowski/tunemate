import { Icon } from '@iconify/react';
import PropTypes from "prop-types";
import { useEffect, useState } from "react";
import axios from "axios";

const icons = [
    "w-7",
    "h-7",
    "mr-2",
    "text-menu"
].join(' ');

const profileSections = [
    "mx-3",
    "text-white"
].join(' ');

function UserHeader({ user }) {

    if (!user) {
        return <div>Loading...</div>;
    }

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
                        "rounded-full"
                    ].join(' ')} style={{
                        backgroundImage: `url(${user.photoUrl})`,
                        backgroundSize: 'cover',
                        backgroundPosition: 'center'
                    }}></div>
                    <div className={"w-64 ml-4"}>
                        <h1 className={"text-4xl font-bold text-white"}>{user.username}</h1>
                        {/*<div className={"text-white flex"}>*/}
                        {/*    /!*<a href={"#"}><h2>0 Followers</h2></a>*!/*/}
                        {/*    /!*<a href={"#"}><h2 className={"ml-2"}>0 Followed</h2></a>*!/*/}
                        {/*</div>*/}
                        <div className={"flex mb-8 mt-1"}>
                            {user.spotifyId && (
                                <a href={`https://open.spotify.com/user/${user.spotifyId}`}>
                                    <Icon icon={"mdi:spotify"} className={icons + " hover:text-green-500"} />
                                </a>
                            )}
                        </div>
                    </div>
                </div>
                <div className={"w-content"}>
                    <div className={"flex mt-12 mx-3 mb-2"}>
                        <a href={"#"}><h3 className={profileSections}>Genres</h3></a>
                        <a href={"#"}><h3 className={profileSections}>Favourites</h3></a>
                        <a href={"#"}><h3 className={profileSections}>Artists</h3></a>
                        <a href={"#"}><h3 className={profileSections}>Playlists</h3></a>
                        <a href={"#"}><h3 className={profileSections}>Reviews</h3></a>
                    </div>
                </div>
            </div>
        </>
    );
}

UserHeader.propTypes = {
    user: PropTypes.shape({
        photoUrl: PropTypes.string,
        username: PropTypes.string,
        spotifyId: PropTypes.string
    })
};

export default UserHeader;
