import Navigation from "../component/menu/Navigation";
import Search from "../component/menu/Search";
import ProfileBanner from "../component/content/banner/ProfileBanner";
import AccountDetails from "../component/form/account/AccountDetails";
import {useEffect, useState} from "react";
import axios from "axios";

function Profile() {
    const [user, setUser] = useState(null);

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
                    <div className={"flex-col w-full my-36"}>
                    </div>
                    <div className={"flex flex-col items-center justify-center my-16"}>
                        <AccountDetails/>
                    </div>
                </div>
            </div>
        </>
    );
}

export default Profile;