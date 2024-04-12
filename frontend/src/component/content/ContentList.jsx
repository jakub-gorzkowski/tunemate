import PropTypes from "prop-types";
import Album from "./Album";

function ContentList(props) {
    const albumsToRender = [];
    for (let i = 0; i < props.size; i++) {
        albumsToRender.push(<Album/>);
    }
    return (
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
                    {albumsToRender}
                </div>
            </div>
        </>
    );
}

ContentList.propTypes = {
    name: PropTypes.string
}

export default ContentList;