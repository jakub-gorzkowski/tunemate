import {Route, Routes} from "react-router-dom";
import HomePage from "./component/page/HomePage";
import Playlists from "./component/page/Playlists";
import Profile from "./component/page/Profile";

function App() {
  return (
      <>
          <div className={"flex flex-auto"}>
              <Routes>
                  <Route path={'/'} element={<HomePage/>}/>
                  <Route path={'/playlists'} element={<Playlists/>}/>

                  <Route path={'/profile'} element={<Profile/>}/>
              </Routes>
          </div>
      </>
  );
}

export default App;
