import {Route, Routes} from "react-router-dom";
import HomePage from "./page/HomePage";
import Favourites from "./page/Favourites";
import Playlists from "./page/Playlists";
import Profile from "./page/Profile";
import Settings from "./page/Settings";
import Release from "./page/Release";
import Playlist from "./page/Playlist";

function App() {
    return (
        <>
            <div className={"flex flex-auto"}>
                <Routes>
                    <Route path={'/'} element={<HomePage/>}/>
                    <Route path={'/favourites'} element={<Favourites/>}/>
                    <Route path={'/playlists'} element={<Playlists/>}/>

                    <Route path={'/profile'} element={<Profile/>}/>
                    <Route path={'/settings'} element={<Settings/>}/>

                    <Route path={'/release/:id'} element={<Release/>}/>
                    <Route path={'/playlist/:id'} element={<Playlist/>}/>
                </Routes>
            </div>
        </>
    );
}

export default App;
