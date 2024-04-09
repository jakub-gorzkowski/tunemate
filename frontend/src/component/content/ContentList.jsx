import PropTypes from "prop-types";
import Album from "./Album";

function ContentList(props) {
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
                <div className={"flex"}>
                    <Album/>
                    <Album/>
                    <Album/>
                    <Album/>
                    <Album/>
                    <Album/>
                </div>
            </div>
        </>
    );
}

ContentList.propTypes = {
    name: PropTypes.string
}

export default ContentList;