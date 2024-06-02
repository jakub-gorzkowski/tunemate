import Navigation from "../component/menu/Navigation";
import Search from "../component/menu/Search";
import ContentList from "../component/content/list/ContentList";
import {useEffect, useState} from "react";
import axios from "axios";

function Playlists() {
    const [user, setUser] = useState(null);
    const [playlists, setPlaylists] = useState([]);

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

        fetchUserData().then(userData => {
            if (userData && userData.spotifyId) {
                fetchPlaylists(userData.spotifyId);
            }
        });
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
                    <ContentList name={"Your playlists"} type={'playlists'} data={playlists} size={12}/>
                    {/*<ContentList name={"Liked playlists"} type={'playlists'} size={12}/>*/}
                    <ContentList name={"Favourite playlists"} type={'playlists'} data={playlists} size={12}/>
                </div>
            </div>
        </>
    );
}

export default Playlists;