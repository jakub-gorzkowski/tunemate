import PropTypes from "prop-types";
import Track from "../list-item/Track";

function TrackList(props) {
    const trackList = [];
    for (let i = 0; i < props.length; i++) {
        trackList.push(<Track/>)
    }

    return (
        <>
            {trackList}
        </>
    );
}

TrackList.propTypes = {
    length: PropTypes.number
};

export default TrackList;