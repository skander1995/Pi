<?php

namespace FaqBundle\Controller;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

use FaqBundle\Entity\Question;
use FaqBundle\Form\Type\QuestionType;
use FaqBundle\Form\Type\QuestionFilterType;
use Symfony\Component\Form\FormInterface;
use Doctrine\ORM\QueryBuilder;

/**
 * Question controller.
 *
 */
class AdminQuestionController extends Controller
{
    /**
     * Lists all Question entities.
     *
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();
        $form = $this->createForm("FaqBundle\Form\Type\QuestionFilterType");
        if (!is_null($response = $this->saveFilter($form, 'question', 'admin_faq'))) {
            return $response;
        }
        $qb = $em->getRepository('FaqBundle:Question')->createQueryBuilder('q');
        $paginator = $this->filter($form, $qb, 'question');
        return $this->render('FaqBundle:Question:index.html.twig', array(
            'form'      => $form->createView(),
            'paginator' => $paginator,
        ));
    }

    /**
     * Finds and displays a Question entity.
     *
     */
    public function showAction(Question $question)
    {
        $deleteForm = $this->createDeleteForm($question->getId(), 'admin_faq_delete');

        return $this->render('FaqBundle:Question:show.html.twig', array(
            'question' => $question,
            'delete_form' => $deleteForm->createView(),        ));
    }

    /**
     * Displays a form to create a new Question entity.
     *
     */
    public function newAction()
    {
        $question = new Question();
        $form = $this->createForm('CovoiturageBundle\Form\CovoiturageType', $question);

        return $this->render('FaqBundle:Question:new.html.twig', array(
            'question' => $question,
            'form'   => $form->createView(),
        ));
    }

    /**
     * Creates a new Question entity.
     *
     */
    public function createAction(Request $request)
    {
        $question = new Question();
        $form = $this->createForm('CovoiturageBundle\Form\CovoiturageType', $question);
        if ($form->handleRequest($request)->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($question);
            $em->flush();

            return $this->redirect($this->generateUrl('admin_faq_show', array('id' => $question->getId())));
        }

        return $this->render('FaqBundle:Question:new.html.twig', array(
            'question' => $question,
            'form'   => $form->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing Question entity.
     *
     */
    public function editAction(Question $question)
    {
        $editForm = $this->createForm('CovoiturageBundle\Form\CovoiturageType', $question, array(
            'action' => $this->generateUrl('admin_faq_update', array('id' => $question->getId())),
            'method' => 'PUT',
        ));
        $deleteForm = $this->createDeleteForm($question->getId(), 'admin_faq_delete');

        return $this->render('FaqBundle:Question:edit.html.twig', array(
            'question' => $question,
            'edit_form'   => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Edits an existing Question entity.
     *
     */
    public function updateAction(Question $question, Request $request)
    {
        $editForm = $this->createForm('FaqBundle\Form\QuestionType', $question, array(
            'action' => $this->generateUrl('admin_faq_update', array('id' => $question->getId())),
            'method' => 'PUT',
        ));
        if ($editForm->handleRequest($request)->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirect($this->generateUrl('admin_faq_edit', array('id' => $question->getId())));
        }
        $deleteForm = $this->createDeleteForm($question->getId(), 'admin_faq_delete');

        return $this->render('FaqBundle:Question:edit.html.twig', array(
            'question' => $question,
            'edit_form'   => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }


    /**
     * Save order.
     *
     */
    public function sortAction($field, $type)
    {
        $this->setOrder('question', $field, $type);

        return $this->redirect($this->generateUrl('admin_faq'));
    }

    /**
     * @param string $name  session name
     * @param string $field field name
     * @param string $type  sort type ("ASC"/"DESC")
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
     * @param string       $name
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
     * @param  string        $name   route/entity name
     * @param  string        $route  route name, if different from entity name
     * @param  array         $params possible route parameters
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
     * @param  FormInterface                                       $form
     * @param  QueryBuilder                                        $qb
     * @param  string                                              $name
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
     * Deletes a Question entity.
     *
     */
    public function deleteAction(Question $question, Request $request)
    {
        $form = $this->createDeleteForm($question->getId(), 'admin_faq_delete');
        if ($form->handleRequest($request)->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($question);
            $em->flush();
        }

        return $this->redirect($this->generateUrl('admin_faq'));
    }

    /**
     * Create Delete form
     *
     * @param integer                       $id
     * @param string                        $route
     * @return \Symfony\Component\Form\Form
     */
    protected function createDeleteForm($id, $route)
    {
        return $this->createFormBuilder(null, array('attr' => array('id' => 'delete')))
            ->setAction($this->generateUrl($route, array('id' => $id)))
            ->setMethod('DELETE')
            ->getForm()
            ;
    }

}
