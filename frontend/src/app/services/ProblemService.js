import axios from "axios";

const PROBLEMS_REST_API_URL = "http://localhost:8080/problems";

class ProblemService {
    
    getProblem() {
        let st1 = axios.get(PROBLEMS_REST_API_URL);
        // console.log(st1);
        return st1;
    }
}

export default new ProblemService();