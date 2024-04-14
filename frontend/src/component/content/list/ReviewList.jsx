import PropTypes from "prop-types";
import Review from "../../review/Review";

function ReviewList(props) {

    const reviewsToRender = [];
    for (var i = 0; i < props.size; i++) {
        reviewsToRender.push(<Review/>)
    }

    return (
        props.title != null &&
        <>
            <div className={"mt-10"}>
                <h1 className={[
                    "text-title",
                    "text-xl",
                    "font-medium",
                    "mx-4",
                    "my-2",
                ].join(' ')}>{props.title}</h1>
                <div className={"my-2"}>
                    {reviewsToRender}
                </div>
            </div>
        </>
    );
}

ReviewList.propTypes = {
    title: PropTypes.string,
    size: PropTypes.number
};

export default ReviewList;