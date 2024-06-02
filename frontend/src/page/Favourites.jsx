import Navigation from "../component/menu/Navigation";
import Search from "../component/menu/Search";
import ContentList from "../component/content/list/ContentList";
import {useEffect, useState} from "react";
import axios from "axios";

function Favourites() {
    const [user, setUser] = useState(null);
    const [playlist, setPlaylist] = useState(null);
    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const user = JSON.parse(localStorage.getItem('user'));
                const token = user && user.jwtToken;

                const response = await axios.get("http://localhost:8080/api/user/get", {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                setUser(response.data);
            } catch (error) {
                console.error( error);
            }
        };

        const fetchFavouritePlaylists = async () => {
                try {
                    const user = JSON.parse(localStorage.getItem('user'));
                    const token = user && user.jwtToken;

                    const response = await axios.get("http://localhost:8080/api/user/favourite-playlists", {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    });
                    setPlaylist(response.data);
                } catch (error) {
                    console.error(error);
                }
        }

        fetchUserData();
        fetchFavouritePlaylists();
    }, []);

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
                <div className={"my-20"}>
                    {/*<ContentList name={"Favourite releases"} type={'release'} size={6}/>*/}
                    {/*<ContentList name={"Favourite playlists"} type={'playlist'} size={6}/>*/}
                    {user && (
                        <>
                            <ContentList name={"Favourite playlists"} type={'playlists'} size={12} data={playlist}/>
                            <ContentList name={"Favourite artists"} type={'user-artists'} size={12} data={user.favouriteArtists}/>
                            <ContentList name={"Favourite genres"} type={'user-genres'} size={12} data={user.favouriteGenres}/>
                        </>
                    )}
                </div>
            </div>
        </>
    );
}

export default Favourites;