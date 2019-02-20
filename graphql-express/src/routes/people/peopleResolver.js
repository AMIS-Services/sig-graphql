import fetch from "node-fetch";
const baseUrl = "http://localhost:3030/api";

const peopleResolver = (_, { id }) => {
  if (id) {
    return fetch(baseUrl + "/people/" + id).then(res => [res.json()]);
  }
  return fetch(baseUrl + "/people").then(res => res.json());
};

export default peopleResolver;
