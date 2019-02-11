const handleResponse = response => {
  if (!response.ok) {
    console.error(`ERROR ${response.status}: ${response.statusText}`);
    return undefined;
  }
  return response.json();
};

export const fetch = (path, options = {}) => {
  const url = `http://localhost:3030/api${path}`;
  const opts = { headers: { "Content-Type": "application/json", Accept: "application/json" }, ...options };
  if (options && options.body) opts.body = JSON.stringify(options.body);
  return window
    .fetch(url, opts)
    .then(handleResponse)
    .catch(error => console.error(`Network error: ${error}`));
};
