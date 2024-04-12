import {Route, Routes} from "react-router-dom";
import HomePage from "./component/page/HomePage";
import Playlists from "./component/page/Playlists";

function App() {
  return (
      <>
          <div className={"flex flex-auto"}>
              <Routes>
                  <Route path={'/'} element={<HomePage/>}/>
                  <Route path={'/playlists'} element={<Playlists/>}/>
              </Routes>
          </div>
      </>
  );
}

export default App;
