import React from "react"
import { useHistory } from "react-router-dom";

const Problem = ({ singleProblem }) => {

    const history = useHistory();
    

    return(
        <div className="row">
            <div className="col-sm-12 grid-margin stretch-card">
                <div class="card text-white bg-dark mb-3">
                    <div class="card-header" >
                        {singleProblem.title}
                    </div>
                <div class="card-body">
                    <div className="row">
                        <span class="col card-text">
                            <ul className="list-inline">
                                <li>
                                    <i className="mdi mdi-bullseye"></i>{singleProblem.difficulty}
                                </li>
                                <li>
                                    <i className=" mdi mdi-account-box-outline"></i>
                                        {singleProblem.author.firstName} {singleProblem.author.lastName}
                                </li>
                                <li></li>
                                <li>{singleProblem.category}</li>
                            </ul>
                        </span>
                        <button style={{ float: "right", height: "32px" }} className="col-lg-4 btn btn-danger btn-sm"
                        onClick={()=>{
                            localStorage.setItem('problemId',singleProblem.id)
                            history.push("/problem");
                        }}>
                            Solve Challenge
                        </button>
                    </div>
                </div>
            </div>
        </div>
        </div>
        
    )
}

export default Problem;