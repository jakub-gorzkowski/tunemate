import RatingDot from "./RatingDot";

function Rating() {
    const rating = [];
    for (let i = 0; i < 10; i++) {
        rating.push(<RatingDot/>);
    }

    return (
        <>
            <div className={"flex flex-auto"}>
                {rating}
            </div>
        </>
    );
}

export default Rating;