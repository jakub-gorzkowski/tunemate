import { Icon } from '@iconify/react';
import React from "react";
import PropTypes from "prop-types";

const icons = [
    "w-7",
    "h-7",
    "mr-2",
    "text-menu"
].join(' ');

function ReleaseHeader({ release }) {

    const handleLinkClick = () => {
        window.location.reload();
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
                        backgroundImage: `url(${release.photoUrl})`,
                        backgroundSize: 'cover',
                        backgroundPosition: 'center'
                    }}></div>
                    <div className={"ml-4 text-white"}>
                        <h1 className={"text-4xl font-bold"}>{release.title}</h1>
                        <div className={"flex my-1"}>
                            <h2>{release.artists.map((artist, index) => (
                                <React.Fragment key={index}>
                                    <a href={`/artist/${artist.spotifyId}`} className="text-thin text-menu hover:underline" onClick={handleLinkClick}>
                                        {artist.name}
                                    </a>
                                    {index !== release.artists.length - 1 && ', '}
                                </React.Fragment>
                            ))}</h2>
                        </div>
                        <p>Available on:</p>
                        <div className={"flex my-1"}>
                            <a href={`https://open.spotify.com/album/${release.spotifyId}`}><Icon icon={"mdi:spotify"}
                                                                                                  className={icons + " hover:text-green-500"}/></a>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

ReleaseHeader.propTypes = {
    release: PropTypes.object.isRequired
};

export default ReleaseHeader;
