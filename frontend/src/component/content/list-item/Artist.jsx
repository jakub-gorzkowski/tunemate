import PropTypes from "prop-types";

function Artist(props) {
    return (
        <>
            <a href={`/artist/${props.data.spotifyId}`}>
                <div className={[
                    "flex",
                    "flex-col",
                    "items-center",
                    "m-1",
                    "pt-4",
                    "px-4",
                    "h-56",
                    "rounded-2xl",
                    "hover:bg-page-hover"
                ].join(' ')}>
                    <div className={[
                        "w-40",
                        "h-40",
                        "bg-cover-placeholder",
                        "rounded-full"
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
                    ].join(' ')}>{props.data.name}</h1>
                </div>
            </a>
        </>
    );
}

Artist.propTypes = {
    data: PropTypes.object.isRequired
};

export default Artist;