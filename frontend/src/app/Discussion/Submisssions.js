import React from 'react';
import { Navbar } from 'react-bootstrap';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import { ProgressBar } from 'react-bootstrap';

function ProblemPage(props) {
    return (
        <div className="main-panel">
            <div>
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item"><a href="#">Library</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Data</li>
                    </ol>
                </nav>
            </div>

            <div className="container float-left">
                <h1 className="font-weight-strong">Problem Title</h1>
            </div>
    
            <div class="container pl-5">
                <table border = "1" >
                    <tr>
                        <td>
                            <Link><h2 className="font-weight-light pl-5 pr-5">Problem</h2></Link>
                        </td>
                        <td>
                            <Link to="/submission"><h2 className="font-weight-light pl-5 pr-5">Submissions</h2></Link>
                        </td>
                        <td>
                            <Link to="/discussion"><h2 className="font-weight-light pl-5 pr-5">Discussion</h2></Link>
                        </td>
                    </tr>
                </table>
            </div>
            <br></br>
            <br></br>
            
            <div className="container sm-12 pl-5">
                <h4 className="font-weight-light">You have not submitted any solutions</h4>
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
                      <tr>
                        <td> 1 </td>
                        <td> Accepted </td>
                        <td>
                          <ProgressBar variant="success" now={100} />
                        </td>
                        <td> Python 3 </td>
                        <td> 10/03/2021 </td>
                        <td> <Link>View Results</Link> </td>
                      </tr>
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

export default ProblemPage;