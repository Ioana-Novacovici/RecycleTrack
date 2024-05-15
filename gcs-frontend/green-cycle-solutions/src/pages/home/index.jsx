import { useContext } from "react";
import AuthContext from "../../api/AuthProvider";
import HomeImage from "../../assets/images/HomePageBg.jpg";
import "../home/style.css";

function Home() {
  const { auth } = useContext(AuthContext);

  return (
    <div>
      <div
        className="card home-card mt-1"
        style={{ color: "#354a3f", backgroundColor: "#dedccb" }}
      >
        <img src={HomeImage} className="card-img" alt="GCS" />
        <div className="card-img-overlay">
          <h5 className="fs-2 card-title ">
            Reduce, Reuse, Recycle - Join our mission to a Greener Planet!
          </h5>
          <h5 className="card-text fs-5 fst-italic fw-normal">
            Green-Cycle-Solutions goal is to make our planet greener through
            selective waste collection and weekly recycling. <br />
            Join us to easily recycle, track your collections and contribute to
            a sustainable future because together, we can make a difference!
          </h5>
          <p className="card-text"></p>
        </div>
      </div>
    </div>
  );
}

export default Home;
