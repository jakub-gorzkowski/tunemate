import {useEffect, useState} from "react";
import axios from "axios";
import PropTypes from "prop-types";
import Review from "../../review/Review";

function ReviewList({ release }) {
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get(`http://localhost:8080/api/releases/reviews/${release.spotifyId}`)
            .then(response => {
                setReviews(response.data);
                setLoading(false);
            })
            .catch(error => {
                setError(error.message);
                setLoading(false);
            });
    }, [release.releaseId]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        release.title != null &&
        <>
            <div className={"mt-10"}>
                <h1 className={[
                    "text-title",
                    "text-xl",
                    "font-medium",
                    "mx-4",
                    "my-2"
                ].join(' ')}>Comments</h1>
                <div className={"my-2"}>
                    {reviews.slice(0, release.size).map((review, index) => (
                        <Review key={index} review={review} />
                    ))}
                </div>
            </div>
        </>
    );
}

ReviewList.propTypes = {
    title: PropTypes.string,
    size: PropTypes.number,
    releaseId: PropTypes.string.isRequired
};

export default ReviewList;