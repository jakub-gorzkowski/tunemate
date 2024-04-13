import {Route, Routes} from "react-router-dom";
import HomePage from "./page/HomePage";
import Playlists from "./page/Playlists";
import Profile from "./page/Profile";
import Settings from "./page/Settings";

function App() {
  return (
      <>
          <div className={"flex flex-auto"}>
              <Routes>
                  <Route path={'/'} element={<HomePage/>}/>
                  <Route path={'/playlists'} element={<Playlists/>}/>

                  <Route path={'/profile'} element={<Profile/>}/>
                  <Route path={'/settings'} element={<Settings/>}/>
              </Routes>
          </div>
      </>
  );
}

export default App;
