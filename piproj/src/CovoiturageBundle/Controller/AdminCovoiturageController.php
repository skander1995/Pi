<?php

namespace CovoiturageBundle\Controller;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

use CovoiturageBundle\Entity\Covoiturage;
use CovoiturageBundle\Form\Type\CovoiturageType;
use CovoiturageBundle\Form\Type\CovoiturageFilterType;
use Symfony\Component\Form\FormInterface;
use Doctrine\ORM\QueryBuilder;

/**
 * Covoiturage controller.
 *
 */
class AdminCovoiturageController extends Controller
{
    /**
     * Lists all Covoiturage entities.
     *
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();
        $form = $this->createForm('CovoiturageBundle\Form\Type\CovoiturageFilterType');
        if (!is_null($response = $this->saveFilter($form, 'covoiturage', 'admin_covoiturage'))) {
            return $response;
        }
        $qb = $em->getRepository('CovoiturageBundle:Covoiturage')->createQueryBuilder('c');
        $paginator = $this->filter($form, $qb, 'covoiturage');
        return $this->render('CovoiturageBundle:Covoiturage:index.html.twig', array(
            'form' => $form->createView(),
            'paginator' => $paginator,
        ));
    }

    /**
     * Finds and displays a Covoiturage entity.
     *
     */
    public function showAction(Covoiturage $covoiturage)
    {
        $deleteForm = $this->createDeleteForm($covoiturage->getId(), 'admin_covoiturage_delete');

        return $this->render('CovoiturageBundle:Covoiturage:show.html.twig', array(
            'covoiturage' => $covoiturage,
            'delete_form' => $deleteForm->createView(),));
    }

    /**
     * Displays a form to create a new Covoiturage entity.
     *
     */
    public function newAction()
    {
        $covoiturage = new Covoiturage();
        $form = $this->createForm('CovoiturageBundle\Form\Type\CovoiturageFilterType', $covoiturage);

        return $this->render('CovoiturageBundle:Covoiturage:new.html.twig', array(
            'covoiturage' => $covoiturage,
            'form' => $form->createView(),
        ));
    }

    /**
     * Creates a new Covoiturage entity.
     *
     */
    public function createAction(Request $request)
    {
        $covoiturage = new Covoiturage();
        $form = $this->createForm('CovoiturageBundle\Form\CovoiturageType', $covoiturage);
        if ($form->handleRequest($request)->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($covoiturage);
            $em->flush();

            return $this->redirect($this->generateUrl('admin_covoiturage_show', array('id' => $covoiturage->getId())));
        }

        return $this->render('CovoiturageBundle:Covoiturage:new.html.twig', array(
            'covoiturage' => $covoiturage,
            'form' => $form->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing Covoiturage entity.
     *
     */
    public function editAction(Covoiturage $covoiturage)
    {
        $editForm = $this->createForm('CovoiturageBundle\Form\CovoiturageType', $covoiturage, array(
            'action' => $this->generateUrl('admin_covoiturage_update', array('id' => $covoiturage->getId())),
            'method' => 'PUT',
        ));
        $deleteForm = $this->createDeleteForm($covoiturage->getId(), 'admin_covoiturage_delete');

        return $this->render('CovoiturageBundle:Covoiturage:edit.html.twig', array(
            'covoiturage' => $covoiturage,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Edits an existing Covoiturage entity.
     *
     */
    public function updateAction(Covoiturage $covoiturage, Request $request)
    {
        $editForm = $this->createForm('CovoiturageBundle\Form\CovoiturageType', $covoiturage, array(
            'action' => $this->generateUrl('admin_covoiturage_update', array('id' => $covoiturage->getId())),
            'method' => 'PUT',
        ));
        if ($editForm->handleRequest($request)->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirect($this->generateUrl('admin_covoiturage_edit', array('id' => $covoiturage->getId())));
        }
        $deleteForm = $this->createDeleteForm($covoiturage->getId(), 'admin_covoiturage_delete');

        return $this->render('CovoiturageBundle:Covoiturage:edit.html.twig', array(
            'covoiturage' => $covoiturage,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }


    /**
     * Save order.
     *
     */
    public function sortAction($field, $type)
    {
        $this->setOrder('covoiturage', $field, $type);

        return $this->redirect($this->generateUrl('admin_covoiturage'));
    }

    /**
     * @param string $name session name
     * @param string $field field name
     * @param string $type sort type ("ASC"/"DESC")
     */
    protected function setOrder($name, $field, $type = 'ASC')
    {
        $this->container->get('request_stack')->getCurrentRequest()
            ->getSession()->set('sort.' . $name, array('field' => $field, 'type' => $type));
    }

    /**
     * @param  string $name
     * @return array
     */
    protected function getOrder($name)
    {
        $session = $this->container->get('request_stack')->getCurrentRequest()->getSession();

        return $session->has('sort.' . $name) ? $session->get('sort.' . $name) : null;
    }

    /**
     * @param QueryBuilder $qb
     * @param string $name
     */
    protected function addQueryBuilderSort(QueryBuilder $qb, $name)
    {
        $alias = current($qb->getDQLPart('from'))->getAlias();
        if (is_array($order = $this->getOrder($name))) {
            $qb->orderBy($alias . '.' . $order['field'], $order['type']);
        }
    }

    /**
     * Save filters
     *
     * @param  FormInterface $form
     * @param  string $name route/entity name
     * @param  string $route route name, if different from entity name
     * @param  array $params possible route parameters
     * @return Response
     */
    protected function saveFilter(FormInterface $form, $name, $route = null, array $params = null)
    {
        $request = $this->container->get('request_stack')->getCurrentRequest();
        $url = $this->generateUrl($route ?: $name, is_null($params) ? array() : $params);
        if ($request->query->has('submit-filter') && $form->handleRequest($request)->isValid()) {
            $request->getSession()->set('filter.' . $name, $request->query->get($form->getName()));

            return $this->redirect($url);
        } elseif ($request->query->has('reset-filter')) {
            $request->getSession()->set('filter.' . $name, null);

            return $this->redirect($url);
        }
    }

    /**
     * Filter form
     *
     * @param  FormInterface $form
     * @param  QueryBuilder $qb
     * @param  string $name
     * @return \Knp\Component\Pager\Pagination\PaginationInterface
     */
    protected function filter(FormInterface $form, QueryBuilder $qb, $name)
    {
        if (!is_null($values = $this->getFilter($name))) {
            if ($form->submit($values)->isValid()) {
                $this->get('lexik_form_filter.query_builder_updater')->addFilterConditions($form, $qb);
            }
        }

        // possible sorting
        $this->addQueryBuilderSort($qb, $name);
        return $this->get('knp_paginator')->paginate($qb, $this->container->get('request_stack')->getCurrentRequest()
            ->query->get('page', 1), 20);
    }

    /**
     * Get filters from session
     *
     * @param  string $name
     * @return array
     */
    protected function getFilter($name)
    {
        return $this->container->get('request_stack')->getCurrentRequest()->getSession()->get('filter.' . $name);
    }

    /**
     * Deletes a Covoiturage entity.
     *
     */
    public function deleteAction(Covoiturage $covoiturage, Request $request)
    {
        $form = $this->createDeleteForm($covoiturage->getId(), 'admin_covoiturage_delete');
        if ($form->handleRequest($request)->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($covoiturage);
            $em->flush();
        }

        return $this->redirect($this->generateUrl('admin_covoiturage'));
    }

    /**
     * Create Delete form
     *
     * @param integer $id
     * @param string $route
     * @return \Symfony\Component\Form\Form
     */
    protected function createDeleteForm($id, $route)
    {
        return $this->createFormBuilder(null, array('attr' => array('id' => 'delete')))
            ->setAction($this->generateUrl($route, array('id' => $id)))
            ->setMethod('DELETE')
            ->getForm();
    }

}
