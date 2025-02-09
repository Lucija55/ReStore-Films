import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";

import { LayoutProvider } from "./assets/layout/layoutcontext";
import { PublicClientApplication } from "@azure/msal-browser";
import { MsalProvider } from "@azure/msal-react";
import { msalConfig } from "./authConfig";

// Bootstrap components
import "bootstrap/dist/css/bootstrap.min.css";

const msalInstance = new PublicClientApplication(msalConfig);

const root = ReactDOM.createRoot(document.getElementById("root"));

/**
 * We recommend wrapping most or all of your components in the MsalProvider component. It's best to render the MsalProvider as close to the root as possible.
 */
root.render(
  <LayoutProvider>
    <React.StrictMode>
      <MsalProvider instance={msalInstance}>
        <App />
      </MsalProvider>
    </React.StrictMode>
  </LayoutProvider>
);
