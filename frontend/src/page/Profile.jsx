import Navigation from "../component/menu/Navigation";
import Search from "../component/menu/Search";
import ContentList from "../component/content/list/ContentList";
import ProfileBanner from "../component/content/banner/ProfileBanner";
import ReviewList from "../component/content/list/ReviewList";
import {useEffect, useState} from "react";
import axios from "axios";

function Profile() {
    const [user, setUser] = useState(null);
    const [playlists, setPlaylists] = useState([]);
    const [playlist, setPlaylist] = useState(null);
    const [loading, setLoading] = useState(true);

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
                return response.data;
            } catch (error) {
                console.error("Error fetching user data:", error);
            }
        };

        const fetchPlaylists = async (spotifyId) => {
            const user = JSON.parse(localStorage.getItem('user'));
            const token = user && user.jwtToken;
            try {
                const response = await axios.get(`http://localhost:8080/api/playlists/get/${spotifyId}/playlists`);
                setPlaylists(response.data);
            } catch (error) {
                console.error("Error fetching playlists:", error);
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

        fetchUserData().then(userData => {
            if (userData && userData.spotifyId) {
                fetchPlaylists(userData.spotifyId);
            }
        });
        fetchFavouritePlaylists();
    }, []);

    useEffect(() => {
        const timeout = setTimeout(() => {
            setLoading(false);
        }, 500);

        return () => clearTimeout(timeout);
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
                "justify-center"
            ].join(' ')}>
                <Search/>
                <div className={"flex-col w-full"}>
                    <div className={"flex-col w-full"}>
                        <ProfileBanner type={"profile"} user={user}/>
                    </div>
                    <div className={"flex flex-col items-center justify-center my-16"}>
                        {user && (
                            <>
                                <ContentList name={"User playlists"} type={'playlists'} size={6} data={playlists}/>
                                <ContentList name={"Favourite playlists"} type={'playlists'} size={12} data={playlist}/>
                                <ContentList name={"Artists"} type={'user-artists'} size={6} data={user.favouriteArtists}/>
                                <ContentList name={"Genres"} type={'user-genres'} size={12} data={user.favouriteGenres}/>
                            </>
                        )}
                    </div>
                </div>
            </div>
        </>
    );
}

export default Profile;
