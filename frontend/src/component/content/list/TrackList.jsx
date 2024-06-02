import PropTypes from "prop-types";
import Track from "../list-item/Track";

function TrackList(props) {
    return (
        <div className="mt-2 mb-8">
            <h1 className="text-title text-xl font-medium mb-4">{props.title}</h1>
            {props.tracks.slice(0, props.length).map(track => (
                <Track
                    key={track.spotifyId}
                    title={track.title}
                    duration={track.duration}
                    artists={track.artists.map(artist => ({
                        id: artist.spotifyId,
                        name: artist.name,
                        photoUrl: artist.photoUrl,
                        followerCount: artist.followerCount
                    }))}
                />
            ))}
        </div>
    );
}

TrackList.propTypes = {
    title: PropTypes.string,
    length: PropTypes.number,
    tracks: PropTypes.arrayOf(PropTypes.shape({
        spotifyId: PropTypes.string,
        title: PropTypes.string,
        duration: PropTypes.number,
        artists: PropTypes.arrayOf(PropTypes.shape({
            spotifyId: PropTypes.string,
            name: PropTypes.string,
            photoUrl: PropTypes.string,
            followerCount: PropTypes.number
        }))
    }))
};

export default TrackList;