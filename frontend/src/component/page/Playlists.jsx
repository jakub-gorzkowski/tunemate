import React from "react";
import Navigation from "../menu/Navigation";
import Search from "../menu/Search";
import ContentList from "../content/ContentList";

function Playlists() {
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
                <div className={"flex-col mt-20"}>
                    <ContentList name={"Your playlists"} size={6}/>
                    <ContentList name={"Liked playlists"} size={6}/>
                    <ContentList name={"Popular playlists"} size={12}/>
                </div>
            </div>
        </>
    );
}

export default Playlists;