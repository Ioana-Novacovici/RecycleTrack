import HomeImage from "../../assets/images/HomePageBg.jpg";

function Home() {
  return (
    <div>
      <div
        className="card m-3"
        style={{ color: "#354a3f", backgroundColor: "#dedccb" }}
      >
        <img src={HomeImage} className="card-img" alt="RT" />
        <div className="card-img-overlay">
          <h5 className="fs-2 card-title ">
            Reduce, Reuse, Recycle - Join our mission to a Greener Planet!
          </h5>
          <h5 className="card-text fs-5 fst-italic fw-normal">
            RecycleTrack goal is to make our planet greener through selective
            waste collection and weekly recycling. <br />
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
