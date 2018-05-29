/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.customComponent;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 *
 * @author cobwi
 */
public class SideBar extends HBox {

    /**
     * @return a control button to hide and show the sidebar
     */
    public Button getControlButton() {
        return controlButton;
    }
    private final Button controlButton;

    /**
     * creates a sidebar containing a vertical alignment of the given nodes
     */
    public SideBar(final double expandedWidth, Node... nodes) {
        getStyleClass().add("sidebar");
        this.setPrefHeight(expandedWidth);
        this.setMinHeight(0);

        // create a bar to hide and show.
        setAlignment(Pos.CENTER);
        getChildren().addAll(nodes);

        // create a button to hide and show the sidebar.
        controlButton = new Button("Collapse");
        controlButton.getStyleClass().add("hide-left");

        // apply the animations when the button is pressed.
        controlButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // create an animation to hide sidebar.
                final Animation hideSidebar = new Transition() {
                    {
                        setCycleDuration(Duration.millis(250));
                    }

                    protected void interpolate(double frac) {
                        final double curWidth = expandedWidth * (1.0 - frac);
                        setPrefHeight(curWidth);
                        setTranslateY(-expandedWidth + curWidth);
                    }
                };
                hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        setVisible(false);
                        controlButton.setText("Show");
                        controlButton.getStyleClass().remove("hide-left");
                        controlButton.getStyleClass().add("show-right");
                    }
                });

                // create an animation to show a sidebar.
                final Animation showSidebar = new Transition() {
                    {
                        setCycleDuration(Duration.millis(250));
                    }

                    protected void interpolate(double frac) {
                        final double curWidth = expandedWidth * frac;
                        setPrefHeight(curWidth);
                        setTranslateY(-expandedWidth + curWidth);
                    }
                };
                showSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        controlButton.setText("Collapse");
                        controlButton.getStyleClass().add("hide-left");
                        controlButton.getStyleClass().remove("show-right");
                    }
                });

                if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
                    if (isVisible()) {
                        hideSidebar.play();
                    } else {
                        setVisible(true);
                        showSidebar.play();
                    }
                }
            }
        });
    }

    public SideBar(final double expandedWidth, Node node, Separator actionnor) {
        getStyleClass().add("sidebar");
        this.setPrefHeight(expandedWidth);
        this.setMinHeight(0);

        // create a bar to hide and show.
        setAlignment(Pos.CENTER);
        getChildren().addAll(node);

        // create a button to hide and show the sidebar.
        controlButton = new Button("Collapse");
        controlButton.getStyleClass().add("hide-left");

        // apply the animations when the button is pressed.
        /*
        actionnor.setOnMouseEntered((event) -> {

            // create an animation to hide sidebar.
            final Animation hideSidebar = new Transition() {
                {
                    setCycleDuration(Duration.millis(250));
                }

                protected void interpolate(double frac) {
                    final double curWidth = expandedWidth * (1.0 - frac);
                    setPrefHeight(curWidth);
                    setTranslateY(-expandedWidth + curWidth);
                }
            };
            hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    setVisible(false);
                    controlButton.setText("Show");
                    controlButton.getStyleClass().remove("hide-left");
                    controlButton.getStyleClass().add("show-right");
                }
            });

            // create an animation to show a sidebar.
            final Animation showSidebar = new Transition() {
                {
                    setCycleDuration(Duration.millis(250));
                }

                protected void interpolate(double frac) {
                    final double curWidth = expandedWidth * frac;
                    setPrefHeight(curWidth);
                    setTranslateY(-expandedWidth + curWidth);
                }
            };
            showSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    controlButton.setText("Collapse");
                    controlButton.getStyleClass().add("hide-left");
                    controlButton.getStyleClass().remove("show-right");
                }
            });

            if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
                if (isVisible()) {
                    hideSidebar.play();
                } else {
                    setVisible(true);
                    showSidebar.play();
                }
            }

        });*/
    }
}
