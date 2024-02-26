import { addons } from '@storybook/addons';
import {create} from "@storybook/theming";
import logo from "../public/logo.png";

addons.setConfig({
    theme:  create({
        base: "light",
        brandTitle: "SmartB SSM SDK",
        brandUrl: "https://github.com/komune-io/ssm",
        brandImage: logo,
        brandTarget: "_self",
        appBg: "#FFFEFB",
        fontBase: '"Montserrat", sans-serif',
        colorPrimary: "#353945",
        colorSecondary: "#353945",
    }),
    showToolbar: false,
});

