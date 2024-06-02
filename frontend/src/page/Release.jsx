import Navigation from "../component/menu/Navigation";
import Search from "../component/menu/Search";
import TrackList from "../component/content/list/TrackList";
import ReleaseBanner from "../component/content/banner/ReleaseBanner";
import Review from "../component/form/review/Review";
import ReviewList from "../component/content/list/ReviewList";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";

function Release() {

    const [release, setRelease] = useState(null);
    const [loading, setLoading] = useState(true);

    const {id} = useParams();

    useEffect(() => {
        axios.get(`http://localhost:8080/api/releases/get/${id}`)
            .then(response => setRelease(response.data))
            .catch(error => console.error(error));
    }, [id]);

    useEffect(() => {
        const timeout = setTimeout(() => {
            setLoading(false);
        }, 500);

        return () => clearTimeout(timeout);
    }, []);

    if (loading) {
        return (
            <div>
                <Navigation/>
                <div className={[
                    "bg-page",
                    "w-screen",
                    "ml-80",
                    "flex",
                    "flex-auto",
                    "justify-center"].join(' ')}>
                    <Search/>
                </div>
            </div>
        );
    }

    return (
        <>
            <Navigation/>
            <div className={[
                "bg-page",
                "w-screen",
                "ml-80",
                "flex",
                "flex-auto",
                "justify-center"].join(' ')}>
                <Search/>
                <div className={"flex-col w-full"}>
                    <ReleaseBanner release={release}/>
                    <div className={"flex flex-col items-center my-16 text-white"}>
                        <TrackList tracks={release.tracks}/>
                        <Review release={release}/>
                        <ReviewList title={"Users reviews"} release={release}/>
                    </div>
                </div>
            </div>
        </>
    );
}

export default Release;