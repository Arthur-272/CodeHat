import React, { useState, useEffect } from 'react';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import { ProgressBar } from 'react-bootstrap';
import backend_url from '../services/api';
import axios from 'axios';

const SubmissionComponent = () => {

  useEffect(() => {
    console.log("In submission tab")
    getAllSubmissions();
    initCount();
 }, []);

 const getAllSubmissions = () =>{
   console.log("in method getAllSubmssions")
   const problemId = localStorage.getItem('problemId')
   const userId = localStorage.getItem('user_id')  
   axios.get(`${backend_url}/user/${userId}/problem/${problemId}/viewSolutions`).then(
      (response) =>{
          console.log(response)
          setSubmissions(response.data);
      },
      (error) =>{
          //error
          console.log(error);
          
      }
  );
};

const initCount =()=>{
  setCount(0)
}

const [submissions,setSubmissions]=useState([])
let [count,setCount]=useState([])

    return (
        <div className="main-panel">
          <div className="container sm-12 pl-5">
            <h4 className="font-weight-light">You have not submitted {submissions.length} solutions.</h4>
        </div>
        <div className="col-xl-12 grid-margin stretch-card">
            <div className="card">
              <div className="card-body">
                <h4 className="card-title">Submissions</h4>
                <div className="table-responsive">
                  <table className="table table-bordered">
                    <thead>
                      <tr>
                        <th> # </th>
                        <th> Result </th>
                        <th> Score </th>
                        <th> Language </th>
                        <th> Date </th>
                        <th></th>
                      </tr>
                    </thead>
                    <tbody>
          {
            submissions.length > 0?submissions.map(submission =>
                      <tr key={submission.id}>
                        <td> {++count} </td>
                        <td> {submission.testCasesPassed}/{submission.testCasesPassed + submission.testCasesFailed} </td>
                        <td>
                          {submission.score}
                        </td>
                        <td> {submission.languageUsed} </td>
                        <td> {submission.solutionSubmittedOn} </td>
                        <td> <Link>View Results</Link> </td>
                      </tr>
                    
          )
             : null
          }
          </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
            

          <div className="container">
                <nav aria-label="...">
                    <ul class="pagination">
                        <li class="page-item disabled">
                            <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
                        </li>
                        <li class="page-item active" aria-current="page">
                            <a class="page-link" href="#">1  <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item">
                            <a class="page-link" href="#">Next</a>
                        </li>
                    </ul>
                </nav>
          </div>
        </div>
    )
}

export default SubmissionComponent;