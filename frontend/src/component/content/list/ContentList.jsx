import PropTypes from "prop-types";
import Release from "../list-item/Release";
import Artist from "../list-item/Artist";
import Playlist from "../list-item/Playlist";
import Genre from "../list-item/Genre";

function ContentList(props) {

    const contentType = (
        props.type === 'release' ? <Release/> :
        props.type === 'artist' ? <Artist/> :
        props.type === 'playlist' ? <Playlist/> :
        props.type === 'genre' ? <Genre/> : null
    );

    const releasesToRender = [];
    for (let i = 0; i < props.size; i++) {
        releasesToRender.push(contentType);
    }
    return (
        props.type != null &&
        <>
            <div>
                <h1 className={[
                    "text-title",
                    "text-xl",
                    "font-medium",
                    "mx-5",
                    "my-2",
                ].join(' ')}>{props.name}</h1>
                <div className={[
                    "flex",
                    "flex-wrap",
                    "w-content"
                ].join(' ')}>
                    {releasesToRender}
                </div>
            </div>
        </>
    );
}

ContentList.propTypes = {
    name: PropTypes.string,
    type: PropTypes.string
};

export default ContentList;