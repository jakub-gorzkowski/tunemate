import PropTypes from "prop-types";

function Playlist(props) {
    return (
        <>
            <a href={`/playlist/${props.data.spotifyId}`}>
                <div className={[
                    "flex",
                    "flex-col",
                    "items-center",
                    "m-1",
                    "p-4",
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
                        "text-lg",
                        "text-title",
                        "font-medium",
                        "mt-2"
                    ].join(' ')}>{props.data.title}</h1>
                </div>
            </a>
        </>
    );
}

Playlist.propTypes = {
    data: PropTypes.object.isRequired
};

export default Playlist;
