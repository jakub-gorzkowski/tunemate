import Navigation from "../component/menu/Navigation";
import Search from "../component/menu/Search";
import ContentList from "../component/content/list/ContentList";
import ProfileBanner from "../component/content/banner/ProfileBanner";
import ReviewList from "../component/content/list/ReviewList";
import {useEffect, useState} from "react";
import axios from "axios";

function Profile() {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
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
                console.error("Error fetching user data:", error);
            }
        };

        fetchData();
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
                <div className={"flex-col w-full"}>
                    <div className={"flex-col w-full"}>
                        <ProfileBanner type={"profile"} user={user}/>
                    </div>
                    <div className={"flex flex-col items-center justify-center my-16"}>
                        <ContentList name={"Genres"} type={'genre'} size={12}/>
                        <ContentList name={"Favourites"} type={'release'} size={6}/>
                        <ContentList name={"Artists"} type={'artist'} size={6}/>
                        <ContentList name={"Playlists"} type={'playlist'} size={6}/>
                        {/*<ReviewList title={"Posted reviews"} size={3}/>*/}
                    </div>
                </div>
            </div>
        </>
    );
}

export default Profile;