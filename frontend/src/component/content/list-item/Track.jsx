import React from "react";
import PropTypes from "prop-types";

function Track(props) {
    const { title, artists, duration } = props;

    const formatDuration = (duration) => {
        const minutes = Math.floor(duration / 60000);
        const seconds = ((duration % 60000) / 1000).toFixed(0);
        return `${minutes}:${(seconds < 10 ? '0' : '')}${seconds}`;
    };

    const handleLinkClick = () => {
        window.location.reload();
    };

    return (
        <div className="px-4 py-1 w-track border-b border-menu flex flex-auto items-center hover:bg-page-hover">
            <div className="w-screen">
                <h1 className="text-white">{title}</h1>
                <h2 className="font-thin text-menu">
                    {artists.map((artist, index) => (
                        <React.Fragment key={index}>
                            <a href={`/artist/${artist.id}`} className="text-thin text-menu hover:underline" onClick={handleLinkClick}>
                                {artist.name}
                            </a>
                            {index !== artists.length - 1 && ', '}
                        </React.Fragment>
                    ))}
                </h2>
            </div>
            <div className="w-full"/>
            <h2 className="font-thin text-menu ml-16">{formatDuration(duration)}</h2>
        </div>
    );
}

Track.propTypes = {
    title: PropTypes.string,
    artists: PropTypes.arrayOf(PropTypes.shape({
        id: PropTypes.string,
        name: PropTypes.string
    })),
    duration: PropTypes.number
};

export default Track;
