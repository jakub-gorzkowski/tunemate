import React from "react";
import PropTypes from "prop-types";
import axios from "axios";

function Release(props) {
    const handleReleaseClick = (e) => {
        e.preventDefault();
        const user = JSON.parse(localStorage.getItem('user'));
        const token = user && user.jwtToken;

        axios.get(`http://localhost:8080/api/releases/get/${props.data.spotifyId}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(response => {
                console.log(response.data);
                window.location.href = `/release/${props.data.spotifyId}`;
            })
            .catch(error => {
                console.error(error);
            });
    };

    return (
        <a href={`/release/${props.data.spotifyId}`} onClick={handleReleaseClick}>
            <div className={[
                "flex",
                "flex-col",
                "items-center",
                "m-1",
                "p-4",
                "max-w-48",
                "h-64",
                "rounded-2xl",
                "hover:bg-page-hover"
            ].join(' ')}>
                <div className={[
                    "w-40",
                    "h-40",
                    "bg-cover-placeholder",
                    "rounded-lg"
                ].join(' ')}
                     style={{
                         backgroundImage: `url(${props.data.photoUrl})`,
                         backgroundSize: 'cover',
                         backgroundPosition: 'center'
                     }}
                ></div>

                <h1 className={[
                    "text-md",
                    "text-title",
                    "font-medium",
                    "mt-2",
                    "truncate"
                ].join(' ')} style={{ maxWidth: "160px" }}>{props.data.title}</h1>
            </div>
        </a>
    );
}

Release.propTypes = {
    data: PropTypes.object.isRequired
};

export default Release;
