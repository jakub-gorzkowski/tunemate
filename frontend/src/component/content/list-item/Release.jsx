import PropTypes from "prop-types";

function Release(props) {
    return (
        <>
            <a href={`/release/${props.data.spotifyId}`}>
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
        </>
    );
}

Release.propTypes = {
    data: PropTypes.object.isRequired
};

export default Release;
