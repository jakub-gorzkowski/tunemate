import {useEffect, useState} from "react";
import axios from "axios";
import ContentList from "../component/content/list/ContentList";
import Navigation from "../component/menu/Navigation";
import Search from "../component/menu/Search";

function HomePage() {
    const [genres, setGenres] = useState([]);
    const [contentLists, setContentLists] = useState([]);

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        const token = user && user.jwtToken;

        axios.get('http://localhost:8080/api/user/genres', {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(response => {
                setGenres(response.data);
            })
            .catch(error => {
                console.error(`Błąd podczas pobierania danych:`, error);
            });
    }, []);

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        const token = user && user.jwtToken;

        Promise.all(genres.map((genre) => {
            return axios.get(`http://localhost:8080/api/artists/${genre.genre}/new`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
        }))
            .then(responses => {
                const newContentLists = responses.map((response, index) => {
                    if (response.data.length > 0) {
                        return <ContentList key={index} name={`New in ${genres[index].genre}`} type={genres[index].genre} size={6} data={response.data}/>
                    }
                });
                setContentLists(newContentLists);
            })
            .catch(error => {
                console.error(error);
            });
    }, [genres]);

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
                    <ContentList name={"New this week"} type={'this-week'} size={6}/>
                    <ContentList name={"New this month"} type={'this-month'} size={6}/>
                    {contentLists}
                </div>
            </div>
        </>
    );
}

export default HomePage;
