import Navigation from "../component/menu/Navigation";
import Search from "../component/menu/Search";
import ContentList from "../component/content/list/ContentList";
import TrackList from "../component/content/list/TrackList";
import PlaylistBanner from "../component/content/banner/PlaylistBanner";
import PlaylistNavigation from "../component/menu/PlaylistNavigation";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";

function Playlist() {

    const [playlist, setPlaylist] = useState(null);
    const [loading, setLoading] = useState(true);

    const {id} = useParams();

    useEffect(() => {
        axios.get(`http://localhost:8080/api/playlists/get/${id}`)
            .then(response => setPlaylist(response.data))
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
                    <PlaylistBanner playlist={playlist}/>
                    <div className={"flex flex-col items-center my-16 text-white"}>
                        <TrackList tracks={playlist.tracks} length={20}/>
                        {/*<PlaylistNavigation/>*/}
                        {/*<ContentList name={"Genres"} type={"genre"} size={6}/>*/}
                    </div>
                </div>
            </div>
        </>
    );
}

export default Playlist;