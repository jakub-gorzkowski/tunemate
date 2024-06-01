import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Navigation from "../component/menu/Navigation";
import Search from "../component/menu/Search";
import ContentList from "../component/content/list/ContentList";
import ArtistBanner from "../component/content/banner/ArtistBanner";
import TrackList from "../component/content/list/TrackList";

function Profile() {
    const [artist, setArtist] = useState(null);
    const [topTracks, setTopTracks] = useState([]);
    const [loading, setLoading] = useState(true);

    const {id} = useParams();

    useEffect(() => {
        axios.get(`http://localhost:8080/api/artists/get/${id}`)
            .then(response => setArtist(response.data))
            .catch(error => console.error(error));

        axios.get(`http://localhost:8080/api/artists/get/${id}/top-tracks`)
            .then(response => setTopTracks(response.data))
            .catch(error => console.error(error));
    }, [id]);

    useEffect(() => {
        const timeout = setTimeout(() => {
            setLoading(false);
        }, 150);

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
                    <div className={"flex-col w-full"}>
                        <ArtistBanner artist={artist}/>
                    </div>
                    <div className={"flex flex-col items-center justify-center my-16"}>
                        <TrackList title={"Popular tracks"} tracks={topTracks} length={10}/>
                        <ContentList name={"Latest releases"} type={'release'} artistId={id} size={6}/>
                        <ContentList name={"Genres"} type={'genre'} id={id} size={6}/>
                    </div>
                </div>
            </div>
        </>
    );
}

export default Profile;
