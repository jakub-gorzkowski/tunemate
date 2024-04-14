import PropTypes from "prop-types";
import Track from "../list-item/Track";

function TrackList(props) {
    const trackList = [];
    for (let i = 0; i < props.length; i++) {
        trackList.push(<Track/>)
    }

    return (
        <>
            <div className={"mt-2 mb-8"}>
                <h1 className={[
                    "text-title",
                    "text-xl",
                    "font-medium",
                    "mb-4",
                ].join(' ')}>{props.title}</h1>
                {trackList}
            </div>
        </>
    );
}

TrackList.propTypes = {
    title: PropTypes.string,
    length: PropTypes.number
};

export default TrackList;